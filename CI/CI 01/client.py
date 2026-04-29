# client.py

import xmlrpc.client

# Connect to the server
proxy = xmlrpc.client.ServerProxy("http://localhost:8000/")

try:
    # Take input from user
    num = int(input("Enter any number: "))

    # Call remote factorial function
    result = proxy.factorial(num)

    # Display result
    print("Factorial is:", result)

except ValueError:
    print("Please enter a valid integer")

except Exception as e:
    print("Error:", e)