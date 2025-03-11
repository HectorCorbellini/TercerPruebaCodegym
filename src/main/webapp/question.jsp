<!DOCTYPE html>
<html>
<head>
    <title>Next Question</title>
    <link rel="stylesheet" type="text/css" href="static/styles.css">
</head>
<body>
<div class="container">
    <h1>${questionText}</h1>
    <form action="" method="post">
        <label for="answer">Your answer:</label>
        <div>
            <input type="radio" id="yes" name="answer" value="Yes" required>
            <label for="yes">Yes</label><br>
        </div>
        <div>
            <input type="radio" id="no" name="answer" value="No">
            <label for="no">No</label><br>
        </div>
        <input type="hidden" name="questionNumber" value="${nextQuestionNumber}">
        <button type="submit">Submit</button>
    </form>
</div>
</body>
</html>
