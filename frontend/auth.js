// Handle Register Form Submission
document.getElementById('registerForm').addEventListener('submit', async (e) => {
  e.preventDefault(); // Prevent page reload on submit

  const form = e.target;
  const data = {
    username: form.username.value,
    email: form.email.value,
    password: form.password.value,
    address: form.address.value,
    paymentMethod: form.paymentMethod.value
  };

  // Send POST request to /register endpoint
  const res = await fetch('http://localhost:8080/api/auth/register', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  });

  // Show response message
  document.getElementById('response').innerText = await res.text();
});

// Handle Login Form Submission
document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault(); // Prevent page reload

  const form = e.target;
  const data = {
    email: form.email.value,
    password: form.password.value
  };

  // Send POST request to /login endpoint
  const res = await fetch('http://localhost:8080/api/auth/login', {
    method: 'POST',
    headers: {'Content-Type': 'application/json'},
    body: JSON.stringify(data)
  });

  // Show login result
  document.getElementById('response').innerText = await res.text();
});
