# Order management demo project

Just a simple springboot demo.

## Order file:

    {
    "items": ["item", "item", ...]
    }

Save as .json.

## To run:

    curl -v -H "content-type: application/json" -H "Accept: application/json" -d @[ORDER FILE].json  http://localhost:8080/orders
