FROM websphere-liberty:webProfile6

RUN chmod 777 -R /config/
RUN chmod 777 -R /opt/
RUN chmod 777 -R /logs/

# Add binary deployment artifact to 'dropins' folder
#RUN wget --directory-prefix=/opt/ibm/wlp/usr/servers/defaultServer/dropins/ $BINARY_DEPLOYMENT_ARTIFACT_URL
COPY backend.war /opt/ibm/wlp/usr/servers/defaultServer/dropins/
COPY health.sh /opt/
RUN chmod 777 /opt/health.sh
# RUN chmod a+rwx /opt/ibm/wlp/output/defaultServer

# IBM WebSphere Liberty license must be accepted
ENV LICENSE $LICENSE
