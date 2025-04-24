document.getElementById("addBookForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const form = e.target;
  const book = {
    title: form.title.value,
    author: form.author.value,
    publisher: form.publisher.value,
    category: form.category.value,
    isbn: form.isbn.value,
    price: parseFloat(form.price.value),
    imageUrl: form.imageUrl.value,
    stock: parseInt(form.stock.value)
  };

  const res = await fetch("http://localhost:8080/api/books/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(book)
  });

  if (res.ok) {
    document.getElementById("addBookMessage").innerText = "🕺 Book added successfully.";
    form.reset();
    loadBooks(); // Refresh list
  } else {
    document.getElementById("addBookMessage").innerText = " Failed to add book.";
  }
});

// Fetch and display all books
async function loadBooks() {
  const res = await fetch("http://localhost:8080/api/books/all");
  const books = await res.json();
  displayBooks(books);
}

// Fetch and display books based on selected sort field and order
async function sortBooks() {
  const sortField = document.getElementById("sortField").value;
  const ascending = document.getElementById("ascending").checked;

  const res = await fetch(`http://localhost:8080/api/books/sort?by=${sortField}&ascending=${ascending}`);
  const books = await res.json();
  displayBooks(books);
}

// Display the book list in the container
function displayBooks(books) {
  const container = document.getElementById("bookList");
  container.innerHTML = ""; // Clear list

  books.forEach(book => {
    const div = document.createElement("div");
    div.style.border = "1px solid #ccc";
    div.style.margin = "10px";
    div.style.padding = "10px";

    div.innerHTML = `
      <strong>${book.title}</strong> by ${book.author}<br>
      <em>${book.publisher}</em> | Category: ${book.category}<br>
      ISBN: ${book.isbn} | Price: €${book.price} | Stock: ${book.stock}<br>
      ${book.imageUrl ? `<img src="${book.imageUrl}" alt="Book cover" width="100">` : ""}
    `;

    container.appendChild(div);
  });
}

// Toggle books display
function toggleBooks() {
  const section = document.getElementById("bookListSection");
  section.style.display = section.style.display === "none" ? "block" : "none";
}

async function loadUsersWithOrders() {
  const res = await fetch("http://localhost:8080/api/books/admin/users-with-orders");
  const data = await res.json();

  const container = document.getElementById("userOrderList");
  container.innerHTML = "";

  data.forEach(entry => {
    const user = entry.user;
    const orders = entry.orders;

    const div = document.createElement("div");
    div.style.border = "1px solid #ccc";
    div.style.margin = "15px";
    div.style.padding = "10px";

    div.innerHTML = `
      <strong>${user.username} (${user.email})</strong><br>
      Address: ${user.address} | Payment: ${user.paymentMethod}<br>
      <u>Orders:</u><br>
      ${orders.length === 0 ? "No orders yet." : ""}
    `;

    // Add each order
    orders.forEach(order => {
      div.innerHTML += `
        <div style="margin-left: 20px;">
          Order ID: ${order.id} | Total: €${order.totalPrice.toFixed(2)} | Date: ${new Date(order.orderDate).toLocaleDateString()}
        </div>
      `;
    });

    container.appendChild(div);
  });
}

// Load all books on page open
window.onload = loadBooks;

document.addEventListener("DOMContentLoaded", () => {
  loadUsersWithOrders();
});