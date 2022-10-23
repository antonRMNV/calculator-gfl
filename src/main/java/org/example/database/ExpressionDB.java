package org.example.database;

import org.example.model.Expression;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ExpressionDB implements DAO<Expression> {

    private Connection getConnection() {
        DBHandler dbHandler = new DBHandler();
        return dbHandler.getConnection();
    }

    @Override
    public Optional get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Expression> getAll() throws SQLException {
        List<Expression> expressionsList = new ArrayList<>();
        String selectAllExpressions = "SELECT * FROM results";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = getConnection().prepareStatement(selectAllExpressions);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        ResultSet resultSet = preparedStatement.executeQuery(selectAllExpressions);
        while(resultSet.next()) {
            String expression = resultSet.getString("expression");
            double result = resultSet.getDouble("result");
            expressionsList.add(new Expression(expression, result));
        }
        return expressionsList;
    }

    @Override
    public void save(Expression expression) throws SQLException {
        String insertExpression = "INSERT INTO results(expression, result) VALUES(?, ?)";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = getConnection().prepareStatement(insertExpression);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        preparedStatement.setString(1, expression.getExpression());
        preparedStatement.setDouble(2, expression.getResult());
        preparedStatement.executeUpdate();
        System.out.printf("Expression \u001B[34m%s\u001B[0m saved to database. Result: \u001B[34m%.2f\u001B[0m\n",
                expression.getExpression(), expression.getResult());
    }

    @Override
    public void update(Expression expression, String[] params) throws SQLException {
        String updateExpression = "UPDATE results SET expression=?, result=? WHERE expression=?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = getConnection().prepareStatement(updateExpression);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        preparedStatement.setString(1, expression.getExpression());
        preparedStatement.setDouble(2, expression.getResult());
        preparedStatement.setString(3, params[0]);
        preparedStatement.executeUpdate();
        System.out.printf("Expression \u001B[34m%s\u001B[0m successfully changed to expression \u001B[34m%s\u001B[0m. " +
                "New result: \u001B[34m%.2f\u001B[0m\n", params[0], expression.getExpression(), expression.getResult());
    }

    @Override
    public void delete(Expression expression) throws SQLException {
        String deleteExpression = "DELETE FROM results WHERE expression=?";
        PreparedStatement preparedStatement;

        try {
            preparedStatement = getConnection().prepareStatement(deleteExpression);
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        preparedStatement.setString(1, expression.getExpression());
        preparedStatement.executeUpdate();
        System.out.printf("Expression \u001B[34m%s\u001B[0m removed from database\n", expression.getExpression());
    }
}
