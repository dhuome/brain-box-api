# Brain Box API

The Brain Box API provides a platform for users to create accounts, log in, and engage with articles. Designed for individuals seeking a platform to share and explore content, this API enables users to post articles, read others' articles, leave comments, and interact with articles through likes and dislikes. While registered users have full access to the platform's features, non-registered users can still browse through articles. With its user-friendly interface and comprehensive features, the Brain Box API fosters a vibrant community where users can discover, share, and discuss diverse topics.

## Documentation

You can also directly interact with the API through Swagger:

[Swagger API](http://localhost:8080/swagger-ui/index.html#/)

## Usage

You can interact with the API using the following endpoints:

| HTTP Verb | Endpoint                       | Description                                     |
|-----------|--------------------------------|-------------------------------------------------|
| POST      | `/api/v1/user`                 | Creates a new user                              |
| POST      | `/api/v1/auth/login`           | Logs in an existing user                        |
| POST      | `/api/v1/article`              | Creates a new article                           |
| GET       | `/api/v1/article`              | Retrieves all articles                          |
| POST      | `/api/v1/article/{id}/comment` | Adds a comment to a specific article            |
| GET       | `/api/v1/article/{id}/comment` | Retrieves all comments for a specific article   |
| GET       | `/api/v1/article/{id}`         | Retrieves a specific article by ID              |
| DELETE    | `/api/v1/article/{id}`         | Deletes a specific article by ID                |
| GET       | `/api/v1/article/{id}/image`   | Retrieves the image of a specific article by ID |
| PUT       | `/api/v1/article/{id}/like`    | Likes a specific article by ID                  |
| PUT       | `/api/v1/article/{id}/dislike` | Dislikes a specific article by ID               |
| PUT       | `/api/v1/article/{id}/enable`  | Enables a specific article by ID                |
| PUT       | `/api/v1/article/{id}/disable` | Disables a specific article by ID               |

For example, to create a new user, you can send a POST request to `/api/v1/user` with the necessary user details in the request body.

## Contributing

We welcome contributions from the community! To contribute to this project, please follow these guidelines:
- Fork the repository
- Create a new branch for your feature or bug fix
- Make your changes
- Test your changes
- Submit a pull request

## License

This project is licensed under the [MIT License](LICENSE). Feel free to use, modify, and distribute this software according to the terms of the license.
