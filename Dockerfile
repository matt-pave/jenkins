FROM jenkins/jenkins:lts-jdk11
ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false
ENV JENKINS_OPTS=--argumentsRealm.roles.user=admin --argumentsRealm.passwd.admin=admin --argumentsRealm.roles.admin=admin

USER root

COPY plugins.txt plugins.txt

RUN /usr/local/bin/install-plugins.sh < plugins.txt

USER jenkins
