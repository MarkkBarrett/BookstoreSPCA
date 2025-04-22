document.addEventListener("DOMContentLoaded", loadCart);

async function loadCart() {
  const userId = localStorage.getItem("userId");

  const res = await fetch(`http://localhost:8080/api/cart/user/${userId}`);
  const cartItems = await res.json();

  const container = document.getElementById("cartItems");
  container.innerHTML = "";

  if (cartItems.length === 0) {
    container.innerHTML = "<p>Your cart is empty.</p>";
    return;
  }

  cartItems.forEach(item => {
    const div = document.createElement("div");
    div.style.border = "1px solid #ccc";
    div.style.margin = "10px";
    div.style.padding = "10px";

	const inputId = `qty-remove-${item.id}`;

    div.innerHTML = `
      <strong>${item.book.title}</strong> by ${item.book.author}<br>
      Quantity in cart: ${item.quantity}<br><br>
      <input type="number" id="${inputId}" value="1" min="1" style="width: 60px;">
      <button onclick="removeFromCart(${item.book.id}, '${inputId}')">Remove</button>
    `;

    container.appendChild(div);
  });
}

async function removeFromCart(bookId, quantityInputId) {
  const userId = localStorage.getItem("userId");
  const quantity = document.getElementById(quantityInputId).value;

  const res = await fetch(`http://localhost:8080/api/cart/remove?userId=${userId}&bookId=${bookId}&quantity=${quantity}`, {
    method: "POST"
  });

  const msg = await res.text();
  alert(msg);
  loadCart(); // refresh cart after removal
}

function goBack() {
  window.location.href = "customer.html";
}

function checkoutCart() {
  alert("Checkout not implemented yet.");
}