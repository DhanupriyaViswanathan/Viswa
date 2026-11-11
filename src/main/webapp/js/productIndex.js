/**
 * Products
 */

function navigateToOrder(productId){
sessionStorage.setItem("productID", productId);
//window.location.href = "order.html"; 
window.location.href = "order_new.html"; 
}


document.addEventListener('keydown', function (event) {
	// Check for F12
	if (event.key === 'F12' || event.keyCode === 123) {
	    event.preventDefault();
	    showSecurityNotice("Developer tools are restricted.");
	    return false;
	}

	// Check for Ctrl + Shift + I / J / C (DevTools shortcuts)
	if (event.ctrlKey && event.shiftKey && (event.key === 'I' || event.key === 'i' || event.key === 'J' || event.key === 'j' || event.key === 'C' || event.key === 'c')) {
	    event.preventDefault();
	    showSecurityNotice("Developer tools are restricted.");
	    return false;
	}

	// Check for Ctrl + U (View Source) or Ctrl + S (Save Page)
	if (event.ctrlKey && (event.key === 'u' || event.key === 'U' || event.code === 'KeyU' || event.key === 's' || event.key === 'S')) {
	    event.preventDefault();
	    showSecurityNotice("Viewing source code is restricted.");
	    return false;
	}
});

// Helper function to show a custom warning notice
function showSecurityNotice(message) {
    let notice = document.getElementById('security-notice');
    if (!notice) {
        notice = document.createElement('div');
        notice.id = 'security-notice';
        notice.style.cssText = "position:fixed; bottom:30px; left:50%; transform:translateX(-50%); background:#222; color:#fff; padding:12px 20px; border-radius:8px; z-index:99999; font-family:sans-serif; font-size:14px; box-shadow:0 4px 12px rgba(0,0,0,0.3); transition:opacity 0.3s;";
        document.body.appendChild(notice);
    }
    notice.innerText = message;
    notice.style.opacity = '1';
    
    clearTimeout(window.noticeTimer);
    window.noticeTimer = setTimeout(() => {
        notice.style.opacity = '0';
    }, 2500);
}