To create a container(launch an application):
    1) Download image.tar file from https: //drive.google.com/file/d/11JlgKUlOnsUroqRJ1JYXkupiUJKv5CcV/view?usp=sharing
    2) Create a Docker image from image.tar using: docker load -i image.tar
    3) Create a Docker container from Docker image using: docker run -p 8080:8080 quiz-rest-web-app:quiz-rest-web-app

To create image from Dockerfile:
    1) Place Dockerfile(from this folder) to Quiz-REST-Web-app
    2) Use: docker build -t quiz-rest-web-app:quiz-rest-web-app .
