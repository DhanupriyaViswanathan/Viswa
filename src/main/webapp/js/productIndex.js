/**
 * Products
 */

function navigateToOrder(productId){
sessionStorage.setItem("productID", productId);
//window.location.href = "order.html"; 
window.location.href = "order_new.html"; 
}
