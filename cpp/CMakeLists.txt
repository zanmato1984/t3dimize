cmake_minimum_required(VERSION 3.10)
project(t3dimize)

set(CMAKE_CXX_STANDARD 11)

add_executable(t3dimize main.cpp include.h)

find_package(PNG REQUIRED)
find_package(OpenGL REQUIRED)
find_package(GLUT REQUIRED)
include_directories(${PNG_INCLUDE_DIR} ${OPENGL_INCLUDE_DIRS}  ${GLUT_INCLUDE_DIRS})

target_link_libraries(t3dimize ${PNG_LIBRARY} ${OPENGL_LIBRARIES} ${GLUT_LIBRARY})