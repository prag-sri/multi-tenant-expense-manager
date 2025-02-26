📌 Expense Management System
A multi-tenant Expense Management System built with Spring Boot, PostgreSQL, RabbitMQ, Redis, and deployed using Docker & Kubernetes.

🚀 Features
✅ User Authentication (Spring Security + JWT)
✅ Role-Based Access Control (USER, ADMIN, SUPER_ADMIN)
✅ Multi-Tenancy Support (Users linked to companies)
✅ Expense Tracking (Create, Update, Delete expenses)
✅ Category Management (Organize expenses by category)
✅ Reports & Analytics (Aggregated reports per user, category, company)
✅ Background Jobs (RabbitMQ for async tasks & email notifications)
✅ Caching (Redis for optimizing API performance)
✅ CI/CD Pipeline (Automated tests & deployments via GitHub Actions)
✅ Docker & Kubernetes Deployment (Fully containerized & scalable)

🛠 Tech Stack
Technology	Usage
Spring Boot	Backend Framework
PostgreSQL	Database
Spring Security + JWT	Authentication & Authorization
RabbitMQ	Background Job Processing
Redis	Caching Layer
Docker & Kubernetes	Containerization & Deployment
GitHub Actions	CI/CD Automation
⚙️ Setup & Installation
1️⃣ Clone the Repository
bash
Copy
Edit
git clone https://github.com/your-username/your-repo.git
cd your-repo
2️⃣ Setup Environment Variables
Create a .env file in the root directory and add:

env
Copy
Edit
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/expense_management
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=postgres
SPRING_REDIS_HOST=localhost
SPRING_RABBITMQ_HOST=localhost
SPRING_MAIL_USERNAME=your-email@example.com
SPRING_MAIL_PASSWORD=your-email-password
JWT_SECRET=your-secret-key
3️⃣ Run via Docker
bash
Copy
Edit
docker-compose up --build
4️⃣ Access the Application
Backend API: http://localhost:8080/api/
RabbitMQ Dashboard: http://localhost:15672/
PostgreSQL Port: 5432
Redis Port: 6379
✅ Running Tests
To execute unit and integration tests:

bash
Copy
Edit
mvn test
🚀 Deployment
This project supports Docker & Kubernetes deployments.

🐳 Docker Deployment
bash
Copy
Edit
docker-compose up --build
☸️ Kubernetes Deployment
bash
Copy
Edit
kubectl apply -f k8s/
📜 License
This project is licensed under the MIT License.

✨ Contributors
Pragati Srivastava - Lead Developer
Open to Contributions! 🚀
