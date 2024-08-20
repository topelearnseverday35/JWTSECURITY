A JWT (JSON Web Token) security system is used to securely transmit information between parties as a JSON object. It is widely used for authentication and authorization purposes.
In this application,I create A CRUD application(A system that allows users to create accounts,get information from the database based off specific request,update user information and delete accounts based on requests)
Question is how can i secure these endpoints and how do i assign roles that people have access to.This is why i implemented the JWT security system.It performs both authorization and authentication on the endpoints.
Authentication: JWTs are issued after a user successfully logs in, serving as a proof of identity. The token is then included in requests to access protected resources, allowing the server to verify the user's identity without needing to store session data.
Authorization: JWTs can carry information about user roles and permissions, enabling the system to control access to resources based on the user's token.
Statelessness: Since JWTs are self-contained and digitally signed, the server does not need to maintain session state, making the system more scalable.
Security: JWTs are typically signed using a secret key or a public/private key pair, ensuring that they cannot be tampered with. They can also be encrypted to protect sensitive data.
Session Management Logic is also implemented here.
