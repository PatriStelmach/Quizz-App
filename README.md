# Quizz-App

An interactive web application for creating, playing, and managing multiplayer quizzes. Built with Spring Boot backend and Vue.js frontend to provide a seamless quiz experience.

## 🎯 Features

- **Quiz Creation**: Create custom quizzes with multiple-choice questions
- **Multiplayer Support**: Play quizzes with multiple participants in real-time
- **Interactive Interface**: Modern, responsive UI built with Vue.js
- **Real-time Updates**: Live score tracking and question progression
- **User Management**: User registration and authentication
- **Quiz Management**: Save, edit, and manage your created quizzes
- **Scoring System**: Automatic scoring and leaderboard functionality

## 🛠️ Technology Stack

### Backend
- **Spring Boot** - Java-based backend framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database operations
- **WebSocket** - Real-time communication for multiplayer features
- **H2/MySQL** - Database (configurable)

### Frontend
- **Vue.js** - Progressive JavaScript framework
- **Vue Router** - Client-side routing
- **Axios** - HTTP client for API calls
- **Tailwind 4 + Shadcn** - Styling and responsive design

## 📋 Prerequisites

Before running this application, make sure you have the following installed:

- Java 21 or higher
- Node.js (18.x or higher)
- npm or yarn
- Maven 3.6+
- Git

## 🚀 Getting Started

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/PatriStelmach/Quizz-App.git
   cd Quizz-App
   ```

2. **Navigate to backend directory**
   ```bash
   cd backend  # or wherever your Spring Boot application is located
   ```

3. **Configure database** (if using MySQL)
   ```properties
   # src/main/resources/application.properties
   spring.datasource.url=jdbc:mysql://localhost:3306/quizz_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the Spring Boot application**
   ```bash
   mvn spring-boot:run
   ```
   
   The backend API will be available at `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd frontend  # or wherever your Vue.js application is located
   ```

2. **Install dependencies**
   ```bash
   npm install
   # or
   yarn install
   ```

3. **Configure API endpoint**
   ```javascript
   // src/config/api.js (or similar)
   const API_BASE_URL = 'http://localhost:10000/';
   ```

4. **Start the development server**
   ```bash
   npm run serve
   # or
   yarn serve
   ```
   
   The frontend will be available at `http://localhost:5173`

## 📁 Project Structure

```
Quizz-App/
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/example/quizzapp/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── model/
│   │   │   │       └── config/
│   │   │   └── resources/
│   │   └── test/
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── views/
│   │   ├── router/
│   │   ├── store/
│   │   └── services/
│   ├── public/
│   └── package.json
└── README.md
```

## 🎮 How to Use

### Creating a Quiz

1. Register/Login to your account
2. Navigate to "Create Quiz" section
3. Add quiz title and description
4. Add questions with multiple choice answers
5. Mark correct answer for each question
6. Save and publish your quiz

### Playing a Quiz

1. Browse available quizzes
2. Join a quiz session
3. Wait for other players (multiplayer mode)
4. Answer questions within the time limit
5. View results and leaderboard

### Multiplayer Mode

1. Host creates a quiz session
2. Share session code with participants
3. Players join using the session code
4. Host starts the quiz when ready
5. Real-time scoring and live updates

## 🔧 Configuration

### Database Configuration

For H2 (Development):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

For MySQL (Production):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quizz_db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
```

### WebSocket Configuration

WebSocket endpoints are configured for real-time multiplayer functionality:
- `/ws/quiz` - Quiz session management
- `/ws/scores` - Live score updates

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm run test:unit
```

## 📦 Deployment

### Backend Deployment
```bash
mvn clean package
java -jar target/quizz-app-*.jar
```

### Frontend Build
```bash
npm run build
```

Deploy the `dist/` folder to your web server.

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 API Documentation

The API documentation is available at `http://localhost:10000/swagger-ui.html` when running the backend application.

### Main Endpoints

- `GET /api/quizzes` - Get all available quizzes
- `POST /api/quizzes` - Create a new quiz
- `GET /api/quizzes/{id}` - Get quiz by ID
