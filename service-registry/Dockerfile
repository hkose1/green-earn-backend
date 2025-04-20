FROM consul:1.15

EXPOSE 8500 8600/udp

COPY consul-config.json /consul/config/consul-config.json

CMD ["agent", "-server", "-ui", "-node=server-1", "-bootstrap-expect=1", "-client=0.0.0.0", "-bind=0.0.0.0", "-data-dir=/consul/data", "-config-dir=/consul/config"] 