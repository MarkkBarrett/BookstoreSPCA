document.addEventListener("DOMContentLoaded", () => {
  const urlParams = new URLSearchParams(window.location.search);
  const bookId = urlParams.get("bookId");
  if (!bookId) {
    alert("No book ID provided!");
    return;
  }

  loadBookDetails(bookId);
  loadRatings(bookId);

  document.getElementById("submitRating").addEventListener("click", () => {
    submitRating(bookId);
  });
});

async function loadBookDetails(bookId) {
  const res = await fetch(`http://localhost:8080/api/books/${bookId}`);
  const book = await res.json();

  // Set book title separately
  document.getElementById("bookTitle").innerText = book.title;

  // Get reference to averageRating container and set it
  const avgRatingText = book.averageRating ? book.averageRating.toFixed(1) : "No ratings yet";
  document.getElementById("averageRating").innerText = `Average Rating: ${avgRatingText}`;

  // Create and set full book info same as catalog
  const detailsDiv = document.createElement("div");
  detailsDiv.innerHTML = `
    <p><strong>Author:</strong> ${book.author}</p>
    <p><strong>Publisher:</strong> ${book.publisher}</p>
    <p><strong>Category:</strong> ${book.category}</p>
    <p><strong>ISBN:</strong> ${book.isbn}</p>
    <p><strong>Price:</strong> €${book.price}</p>
    <p><strong>Stock:</strong> ${book.stock}</p>
    ${book.imageUrl ? `<img src="${book.imageUrl}" width="100">` : ""}
  `;

  // Append below
  document.getElementById("bookTitle").insertAdjacentElement("afterend", detailsDiv);
}

async function loadRatings(bookId) {
  const res = await fetch(`http://localhost:8080/api/ratings/book/${bookId}`);
  const ratings = await res.json();

  const container = document.getElementById("ratingList");
  container.innerHTML = "<h3>All Ratings</h3>";

  if (ratings.length === 0) {
    container.innerHTML += "<p>No ratings yet.</p>";
    return;
  }

  ratings.forEach(r => {
    const div = document.createElement("div");
    div.style.border = "1px solid #ccc";
    div.style.margin = "10px";
    div.style.padding = "10px";
    div.innerHTML = `
      <strong>★ ${r.stars}</strong><br>
      <em>${r.comment}</em><br>
      <small>by User ID: ${r.user.id}</small>
    `;
    container.appendChild(div);
  });
}

async function submitRating(bookId) {
  const userId = localStorage.getItem("userId");
  const stars = document.getElementById("stars").value;
  const comment = document.getElementById("comment").value;

  const url = `http://localhost:8080/api/ratings/submit?userId=${userId}&bookId=${bookId}&stars=${stars}&comment=${encodeURIComponent(comment)}`;
  const res = await fetch(url, { method: "POST" });

  const msg = await res.text();
  alert(msg);
  loadBookDetails(bookId);
  loadRatings(bookId);
}
