# SpringBoot Microservices Starter Kit

Welcome to the SpringBoot Microservices Starter Kit repository! This repository serves as a boilerplate for setting up Spring Boot microservices with various essential features pre-configured. Whether you're just starting your journey with microservices or looking for a robust foundation to build upon, this starter kit is designed to accelerate your development process.

## Features Included

1. **Naming Server**: Eureka Naming Server for service discovery.
2. **Gateway**: Zuul API Gateway for routing and filtering requests.
3. **Template Service**: Template service that can be easily replicated and extended.
4. **Authentication**: JWT authentication and token validation via the gateway.
5. **Database Connection**: Configurations for connecting to databases.
6. **Auditing**: Configuration and models for auditing purposes, including created and updated dates, and created by and updated by fields.
7. **User Roles**: Role-based access control for managing user permissions.
8. **Proxies with Feign**: Simplified communication between microservices and REST APIs using Feign.

## Getting Started

To get started with using this starter kit, follow these steps:

1. **Clone the Repository**: Clone this repository to your local machine using the following command: (git clone https://github.com/your-username/SpringBoot-Microservices-StarterKit.git)
   
3. **Configuration**: Customize the configurations according to your requirements, such as database settings, JWT secret, etc.
   Insert default roles into the user-database:
   INSERT INTO roles(name) VALUES('ROLE_USER');
   INSERT INTO roles(name) VALUES('ROLE_ADMIN');

6. **Build and Run**: Build and run the microservices using your favorite IDE or build tools like Maven or Gradle.

7. **Explore and Extend**: Explore the provided services and functionalities. Extend them to suit your specific use cases and requirements.

8. **Deploy**: Once you're satisfied with your modifications, deploy the microservices to your desired environment.
   
9. **Template Service**: Details on how to customize the template service are in the read-me file in template-service directory.

10. **Contribute**: If you find bugs or have suggestions for improvements, feel free to contribute by opening issues or submitting pull requests.

## Contributing

Contributions are welcome! If you encounter any issues or have ideas for improvements, please open an issue on the GitHub repository. Pull requests are also encouraged.

## License

This project is licensed under the [MIT License](LICENSE).

