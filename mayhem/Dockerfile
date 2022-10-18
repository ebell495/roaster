FROM maven:3-openjdk-11 as builder

# Download Jazzer
RUN wget https://github.com/CodeIntelligenceTesting/jazzer/releases/download/v0.12.0/jazzer-linux-x86_64.tar.gz && tar -xvzf jazzer-linux-x86_64.tar.gz

COPY . /project
WORKDIR /project
# Build the library jar and download the dependencies
RUN mvn clean package -DskipTests -Dmaven.javadoc.skip
# Copy the jar to the fuzz folder and copy the dependencies to the fuzz folder
RUN mkdir ./fuzz/deps && find ./ -name "roaster*.jar" -exec cp {} "./fuzz/deps/" \;
# Make a 'superjar' of all of the dependencies
RUN mkdir /project/depstmp/ && cd /project/depstmp/ && for f in /project/fuzz/deps/*; do jar -xf $f; done && jar -cf /project/fuzz/src/libs.jar .
WORKDIR /project/fuzz/src
# # Build the fuzz target
RUN javac -cp "/project/fuzz/src/libs.jar" RoasterFuzz.java && jar cf RoasterFuzz.jar RoasterFuzz.class && chmod u+x RoasterFuzz.jar


ENTRYPOINT []
CMD [ "/jazzer", "--cp=/project/fuzz/src/RoasterFuzz.jar:/project/fuzz/src/libs.jar", "--target_class=RoasterFuzz" ]
