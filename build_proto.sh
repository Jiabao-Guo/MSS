PROTO_SRC=src/main/resources/proto/
PROTO_DEST=src/main/java/

protoc --proto_path=${PROTO_SRC} --java_out=${PROTO_DEST} ${PROTO_SRC}/*.proto

