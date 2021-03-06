<h3>Description</h3>
This project aims to represent a model for an online bookstore where users can join monthly book subscritpions, rent or
purchase books and use the rating system to give reviews for their favourite books.

<h3>Business requirements</h3>

    1.To use this service, one must make an account using personal information such as first and last name and email.
    2.Users can join monthly subscriptions for certain book genres.
    3.Users can also rent a book for a specific amount of time for a fee.
    4.Like an usual book store, it's books can also be purchased.
    5.Users can leave reviews on certain books using a five-star system accompanied with a personalised message.
    6.All users can access the whole catalogue of books or can search by genre, author or publisher.
    7.Certain publishers can contribute to the bookstore with more books.
    8.There is a second section of the bookstore where one can purchase audio-books.
    9.In addition, audio-books will present their narrators and their narrated duration in time.
    10.As audio-books are digital, their rental is replaced by a monthly subscription which offers access to the whole catalogue of audio-books.

<h3>Main features</h3>

    1.Ability to make an account.
    2.Users can access the entire book catalogue or search by genre.
    3.Users can rent or purchase books and can also join monthly book subscriptions.
    4.Users can leave reviews for books.

<h3>Postman Collection</h3>

Located at "src/main/resources/Java Book Store.postman_collection.json"

Request example: http://{{host}}/{{apiEndpoint}}/accounts

<h4>Needed Environment for Variables:</h4>

host: "localhost:8080", apiEndpoint: "v1"

<h4>Swagger Documentation:</h4>

http://localhost:8080/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/#