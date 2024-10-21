
# Dynamic PDF Generator

A Spring Boot application that generates PDFs based on user input, including seller and buyer details, as well as itemized lists. The application supports hash-based caching to avoid duplicate PDF generation.





## Features

- Generates PDF documents using iText.
- Caches generated PDFs to avoid duplication using SHA-256 hashing.
- Provides a RESTful API to generate PDFs from JSON requests.


## Technologies Used

- **Java**: Programming language for the application.
- **Spring Boot**: Framework for building the application.
- **iText**: Library for PDF generation.
- **Lombok**: Used for reducing boilerplate code.


## Installation

1. Clone the repository:

```bash
 git clone https://github.com/yourusername/DynamicPDFGenerator.git
cd DynamicPDFGenerator
```
2. Build the project:
```bash
    mvn clean install
 ```
3. Run the application:
```bash
    mvn spring-boot:run
```
4. The application will run on ``http://localhost:8080``
## API Reference

### 1. Generate PDF

**Endpoint**: `/api/pdf/generate`  
**Method**: `POST`  
**Description**: Generates a PDF based on the provided seller, buyer, and item details.

#### Request

**Headers**:
- `Content-Type: application/json`

**Request Body**:
```json
{
    "seller": "XYZ Pvt. Ltd.",
    "sellerGstin": "29AABBCCDD121ZD",
    "sellerAddress": "New Delhi, India",
    "buyer": "Vedant Computers",
    "buyerGstin": "29AABBCCDD131ZD",
    "buyerAddress": "New Delhi, India",
    "items": [
        {
            "name": "Item 1",
            "quantity": "2",
            "rate": 100.00,
            "amount": 200.00
        },
        {
            "name": "Item 2",
            "quantity": "1",
            "rate": 150.00,
            "amount": 150.00
        }
    ]
}
```
**Response**:

Success Response (HTTP Status 200):
 
       Content-Type: application/pdf
       Body: A PDF file containing the generated invoice.
![Logo](https://github.com/GautamAgnihotri/DynamicPDFGenerator/blob/main/outputImage/img.png)

Error Response (HTTP Status 500):

        Body: An error message indicating the failure to generate the PDF.



