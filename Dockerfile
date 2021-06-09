FROM jenkins/jenkins:lts-jdk11
ENV JAVA_OPTS=-Djenkins.install.runSetupWizard=false
ENV JENKINS_OPTS=--argumentsRealm.roles.user=admin --argumentsRealm.passwd.admin=admin --argumentsRealm.roles.admin=admin

USER root

RUN /usr/local/bin/install-plugins.sh credentials maven-plugin workflow-cps workflow-cps-global-lib workflow-aggregator git

USER jenkins
