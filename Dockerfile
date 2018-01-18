FROM websphere-liberty:webProfile6

# grant permission
RUN chmod 777 -R /config/
RUN chmod 777 -R /opt/
RUN chmod 777 -R /logs/

# Add binary deployment artifact to 'dropins' folder
COPY backend.war /opt/ibm/wlp/usr/servers/defaultServer/dropins/

# health check
COPY health.sh /opt/
RUN chmod 777 /opt/health.sh

# IBM WebSphere Liberty license must be accepted
ENV LICENSE $LICENSE
