# server.py

from xmlrpc.server import SimpleXMLRPCServer

# Function to calculate factorial
def factorial(n):
    if n < 0:
        return "Error: Factorial not defined for negative numbers"

    result = 1
    for i in range(1, n + 1):
        result *= i

    return str(result)   # Convert to string (IMPORTANT for large values)


# Create server on localhost port 8000
server = SimpleXMLRPCServer(("localhost", 8000))

print("Server is running on http://localhost:8000/")

# Register function so client can call it remotely
server.register_function(factorial, "factorial")

# Keep server running forever
server.serve_forever()