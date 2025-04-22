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

async function addToCart(bookId, quantityInputId) {
  const quantity = document.getElementById(quantityInputId).value;
  const userId = localStorage.getItem("userId");

  const res = await fetch(`http://localhost:8080/api/cart/add?userId=${userId}&bookId=${bookId}&quantity=${quantity}`, {
    method: "POST"
  });

  const msg = await res.text();
  alert(msg);
}

async function undoLastCartAction() {
  const res = await fetch("http://localhost:8080/api/cart/undo", {
    method: "POST"
  });

  const msg = await res.text();
  alert(msg);
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

	// Unique quantity input ID per book
    const quantityInputId = `qty-${book.id}`;

    div.innerHTML = `
      <strong>${book.title}</strong> by ${book.author}<br>
      <em>${book.publisher}</em> | Category: ${book.category}<br>
      ISBN: ${book.isbn} | Price: €${book.price} | Stock: ${book.stock}<br>
      ${book.imageUrl ? `<img src="${book.imageUrl}" alt="Book cover" width="100">` : ""}
      <br><br>
      <input type="number" id="${quantityInputId}" value="1" min="1" style="width: 60px;">
      <button onclick="addToCart(${book.id}, '${quantityInputId}')">Add to Cart</button>
    `;


    container.appendChild(div);
  });
}

// Load all books on page open
window.onload = loadBooks;