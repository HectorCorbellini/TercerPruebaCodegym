<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="static/styles.css">
</head>
<body class="error-body">
<div class="container error-container">
    <h1>You've failed</h1>
    <p>${message}</p>
    <!-- Button to restart the game -->
    <form action="question" method="get">
        <button type="submit">Start Again</button>
    </form>
</div>
</body>
</html>
