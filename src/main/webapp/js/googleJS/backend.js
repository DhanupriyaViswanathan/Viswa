function doPost(e) {
  try {
    if (!e || !e.postData || !e.postData.contents) {
      return ContentService.createTextOutput(JSON.stringify({ status: "ERROR", message: "No post data received" }))
        .setMimeType(ContentService.MimeType.JSON);
    }

    var data = JSON.parse(e.postData.contents);
    var ss = SpreadsheetApp.getActiveSpreadsheet();
    var action = data.action;

    if (action === "createOrder") {
      // 1. Check or Append User Record
      var userSheet = ss.getSheetByName("User");
      if (!userSheet) {
        userSheet = ss.insertSheet("User");
        userSheet.appendRow(["userId", "created_date", "customerName", "email", "mobile", "address"]);
      }
      
      var lastUserRow = userSheet.getLastRow();
      var userId = null;
      var mobileToCheck = data.mobile ? data.mobile.toString().trim() : "";
      var emailToCheck = data.email ? data.email.toString().trim().toLowerCase() : "";

      if (lastUserRow >= 2) {
        var numRows = lastUserRow - 1;
        var userValues = userSheet.getRange(2, 1, numRows, 6).getValues();
        for (var u = 0; u < userValues.length; u++) {
          var row = userValues[u];
          var existingUserId = row[0];
          var existingEmail = row[3] ? row[3].toString().trim().toLowerCase() : "";
          var existingMobile = row[4] ? row[4].toString().trim() : "";

          if ((mobileToCheck && existingMobile === mobileToCheck) || (emailToCheck && existingEmail === emailToCheck)) {
            userId = existingUserId;
            var rowIndex = u + 2;
            userSheet.getRange(rowIndex, 2).setValue(new Date());
            userSheet.getRange(rowIndex, 3).setValue(data.customerName);
            userSheet.getRange(rowIndex, 6).setValue(data.address);
            break;
          }
        }
      }

      if (userId === null) {
        userId = lastUserRow <= 1 ? 1 : lastUserRow;
        userSheet.appendRow([
          userId,
          new Date(),
          data.customerName,
          data.email,
          data.mobile,
          data.address
        ]);
      }

      // 2. Append Order Record
      var orderSheet = ss.getSheetByName("Order");
      if (!orderSheet) {
        throw new Error("Sheet 'Order' not found in spreadsheet.");
      }

      orderSheet.appendRow([
        data.orderId,
        new Date(),
        data.customerName,
        data.productName,
        data.weight,
        data.quantity,
        data.unitRate,
        data.subtotal,
        data.gst,
        data.totalPrice,
        data.mobile,
        data.email,
        data.address,
        "PENDING",        // status
        data.productId,   // productId
        userId,           // userId
        "UNSHIPPED",      // shipment_status
        new Date()        // updated_date
      ]);

      // 3. Append Initial Pending Payment Record
      var paymentSheet = ss.getSheetByName("Payment");
      if (paymentSheet) {
        var initialPendingTxnId = "PENDING_TXN_" + Math.floor(100000 + Math.random() * 900000);
        paymentSheet.appendRow([
          data.orderId,
          initialPendingTxnId,
          "PENDING",
          data.totalPrice,
          new Date()
        ]);
      }

      return ContentService.createTextOutput(JSON.stringify({ status: "SUCCESS", userId: userId }))
        .setMimeType(ContentService.MimeType.JSON);
    }

    else if (action === "updatePayment") {
      var paymentSheet = ss.getSheetByName("Payment");
      if (!paymentSheet) {
        throw new Error("Sheet 'Payment' not found.");
      }
      
      var lastPayRow = paymentSheet.getLastRow();
      var orderIdStr = data.orderId ? data.orderId.toString().trim() : "";
      
      // 1. Update Payment sheet
      var updated = false;
      if (lastPayRow >= 2) {
        var payValues = paymentSheet.getRange(2, 1, lastPayRow - 1, 1).getValues();
        for (var j = 0; j < payValues.length; j++) {
          var rowOrderId = payValues[j][0];
          if (rowOrderId && rowOrderId.toString().trim() === orderIdStr) {
            var targetRow = j + 2;
            paymentSheet.getRange(targetRow, 2).setValue(data.paymentId);
            paymentSheet.getRange(targetRow, 3).setValue("PAID");
            paymentSheet.getRange(targetRow, 4).setValue(data.amount);
            paymentSheet.getRange(targetRow, 5).setValue(new Date());
            updated = true;
            break;
          }
        }
      }

      if (!updated) {
        paymentSheet.appendRow([
          data.orderId,
          data.paymentId,
          "PAID",
          data.amount,
          new Date()
        ]);
      }

      // 2. Update Order sheet status to PAID and retrieve customer details
      var orderSheet = ss.getSheetByName("Order");
      if (!orderSheet) {
        throw new Error("Sheet 'Order' not found.");
      }
      
      var lastRow = orderSheet.getLastRow();
      var customerName = data.customerName || "";
      var productName = data.productName || "";
      var customerEmail = data.email || "";

      if (lastRow >= 2) {
        var orderRows = orderSheet.getRange(2, 1, lastRow - 1, 18).getValues();
        for (var i = 0; i < orderRows.length; i++) {
          var orderIdVal = orderRows[i][0];
          if (orderIdVal && orderIdVal.toString().trim() === orderIdStr) {
            var orderRowIndex = i + 2;
            orderSheet.getRange(orderRowIndex, 14).setValue("PAID");
            orderSheet.getRange(orderRowIndex, 18).setValue(new Date());
            
            // Fallback to sheet row if missing from request payload
            if (!customerName) customerName = orderRows[i][2];
            if (!productName) productName = orderRows[i][3];
            if (!customerEmail) customerEmail = orderRows[i][11];
            break;
          }
        }
      }

      // 3. Send Automated Confirmation Email
      var emailSent = false;
      var emailErrorMsg = "";

      if (customerEmail) {
        try {
          var subject = "Order Confirmed! #VSR" + data.orderId + " - VSR Food Products";
          var body = "Hi " + customerName + ",\n\n" +
                     "Thank you for ordering with VSR Food Products!\n\n" +
                     "Your payment has been successfully received and verified.\n\n" +
                     "Order Summary:\n" +
                     "- Order ID: VSR" + data.orderId + "\n" +
                     "- Product: " + productName + "\n" +
                     "- Amount Paid: ₹" + data.amount + "\n" +
                     "- Payment ID: " + data.paymentId + "\n\n" +
                     "We are currently preparing your items for shipment from our unit in Achettipalli, Hosur.\n\n" +
                     "Warm regards,\nVSR Food Products\nFSSAI Reg. No. 22426099000821";

          GmailApp.sendEmail(customerEmail, subject, body, {
            name: "VSR Food Products"
          });
          emailSent = true;
        } catch (emailErr) {
          emailErrorMsg = emailErr.toString();
          console.error("Error sending confirmation email: " + emailErrorMsg);
        }
      } else {
        emailErrorMsg = "No email address found for order ID " + data.orderId;
      }

      return ContentService.createTextOutput(JSON.stringify({ 
        status: "SUCCESS", 
        emailSent: emailSent,
        emailError: emailErrorMsg || undefined
      })).setMimeType(ContentService.MimeType.JSON);
    }

    else if (action === "cancelOrder") {
      var orderIdStr = data.orderId ? data.orderId.toString().trim() : "";
      if (!orderIdStr) {
        return ContentService.createTextOutput(JSON.stringify({ status: "ERROR", message: "Missing orderId" }))
          .setMimeType(ContentService.MimeType.JSON);
      }

      var orderSheet = ss.getSheetByName("Order");
      if (orderSheet) {
        var lastOrderRow = orderSheet.getLastRow();
        if (lastOrderRow >= 2) {
          var orderVals = orderSheet.getRange(2, 1, lastOrderRow - 1, 1).getValues();
          for (var i = orderVals.length - 1; i >= 0; i--) {
            if (orderVals[i][0] && orderVals[i][0].toString().trim() === orderIdStr) {
              orderSheet.deleteRow(i + 2);
              break;
            }
          }
        }
      }

      var paymentSheet = ss.getSheetByName("Payment");
      if (paymentSheet) {
        var lastPayRow = paymentSheet.getLastRow();
        if (lastPayRow >= 2) {
          var payVals = paymentSheet.getRange(2, 1, lastPayRow - 1, 1).getValues();
          for (var j = payVals.length - 1; j >= 0; j--) {
            if (payVals[j][0] && payVals[j][0].toString().trim() === orderIdStr) {
              paymentSheet.deleteRow(j + 2);
              break;
            }
          }
        }
      }

      return ContentService.createTextOutput(JSON.stringify({ status: "SUCCESS" }))
        .setMimeType(ContentService.MimeType.JSON);
    }

  } catch (error) {
    return ContentService.createTextOutput(JSON.stringify({ status: "ERROR", message: error.toString() }))
      .setMimeType(ContentService.MimeType.JSON);
  }
}