#!/C/Git/bin/bash.exe
# docker build -t my_image . # . - path to dockerfile
docker stop basejava-postgres  # use --rm option
docker rm -f basejava-postgres 2>/dev/null || true
docker run \
    --name basejava-postgres \
    -d \
    -p 5431:5432/tcp \
    -e POSTGRES_USER=basejava \
    -e POSTGRES_PASSWORD=123456 \
    -e POSTGRES_DB=resumes \
    postgres

#docker run \
#    --name basejava-tomcat \
#    -d \
#    -p 8080:8080/tcp \
#    tomcat:8.0

#docker cp config/tomcat-users.xml basejava-tomcat:/usr/local/tomcat/conf/tomcat-users.xml
#https://hub.docker.com/_/tomcat
#https://wiki.merionet.ru/servernye-resheniya/9/kak-rabotat-s-dockerfile/
# https://www.cprime.com/resources/blog/deploying-your-first-web-app-to-tomcat-on-docker/
# См. заметку в MyNote что такое работающий продукт и как его делать.