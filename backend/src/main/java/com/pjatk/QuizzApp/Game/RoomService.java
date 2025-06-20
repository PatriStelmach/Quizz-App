package com.pjatk.QuizzApp.Game;

import com.pjatk.QuizzApp.Answer.Answer;
import com.pjatk.QuizzApp.Exceptions.UserNotFoundException;
import com.pjatk.QuizzApp.Game.Memory.Room;
import com.pjatk.QuizzApp.Game.Memory.RoomQuestion;
import com.pjatk.QuizzApp.Question.Question;
import com.pjatk.QuizzApp.Question.QuestionRepository;
import com.pjatk.QuizzApp.Quiz.Quiz;
import com.pjatk.QuizzApp.Quiz.QuizRepository;
import com.pjatk.QuizzApp.Quiz.UserQuizScore;
import com.pjatk.QuizzApp.Quiz.UserQuizScoreRepository;
import com.pjatk.QuizzApp.User.User;
import com.pjatk.QuizzApp.User.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@AllArgsConstructor
public class RoomService {

    private final Map<String, Room> rooms = new ConcurrentHashMap<>();

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final UserQuizScoreRepository userQuizScoreRepository;

    public Room createRoom(Integer quizId) throws AccessDeniedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {

            String id = UUID.randomUUID().toString().substring(0, 6);
            Room room = new Room(id, authentication.getName(), quizId);
            room.setOwnerName(authentication.getName());
            room.getPlayers().add(authentication.getName());

            rooms.put(id, room);
            return room;
        } else {
            throw new AccessDeniedException("You don't have permission to create a new quiz room");
        }
    }

    public Room getRoom(String id) {
        return rooms.get(id);
    }

    public List<RoomQuestion> loadRoomQuestionsFromDB(Integer quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        List<Question> dbQuestions = questionRepository.findByQuizId(quizId);
        int timePerQuestion = (int) Math.floor((double) quiz.getTimeLimit() * 60 / dbQuestions.size());

        List<RoomQuestion> roomQuestions = new ArrayList<>();
        for (Question q : dbQuestions) {
            List<String> answers = q.getAnswers().stream()
                    .map(Answer::getAnswerText)
                    .toList();


            int correctIndex = -1;//none
            for (int i = 0; i < q.getAnswers().size(); i++) {
                if (q.getAnswers().get(i).isCorrect()) {
                    correctIndex = i;
                    break;
                }
            }

            roomQuestions.add(new RoomQuestion(
                    q.getQuestion(),
                    answers,
                    correctIndex,
                    timePerQuestion
            ));
        }

        return roomQuestions;
    }

    @Transactional
    public void saveQuizResults(Room room) {
        Quiz quiz = quizRepository.findById(room.getQuizId())
                .orElseThrow(() -> new IllegalArgumentException("Quiz not found"));

        for (String playerName : room.getPlayers()) {
            User user = userRepository.findByUsername(playerName)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + playerName));

            int score = room.getPlayerScores().getOrDefault(playerName, 0);

            UserQuizScore userQuizScore = new UserQuizScore();
            userQuizScore.setUser(user);
            userQuizScore.setQuiz(quiz);
            userQuizScore.setScore(score);

            userQuizScoreRepository.save(userQuizScore);
        }
    }
}
