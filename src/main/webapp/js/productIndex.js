/**
 * Products
 */

function navigateToOrder(productId){
sessionStorage.setItem("productID", productId);
window.location.href = "order.html"; 
}
