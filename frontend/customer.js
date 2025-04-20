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

// Load all books on page open
window.onload = loadBooks;