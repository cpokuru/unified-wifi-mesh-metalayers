SUMMARY = "MySQL/MariaDB database connector for C++"
DESCRIPTION = "MySQL Connector/C++ is a MySQL database connector for C++, providing access to MySQL and MariaDB databases"
HOMEPAGE = "https://dev.mysql.com/downloads/connector/cpp/"
LICENSE = "GPL-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d5ad936b92355df92ed0d61bc9b54577"

DEPENDS = "boost mysql"

inherit cmake

SRC_URI = "https://dev.mysql.com/get/Downloads/Connector-C++/mysql-connector-c++-${PV}.tar.gz"

S = "${WORKDIR}/mysql-connector-c++-${PV}"

EXTRA_OECMAKE += " \
    -DBUILD_STATIC=OFF \
    -DBUILD_SHARED=ON \
    -DMYSQLCLIENT_STATIC_BINDING=OFF \
    -DMYSQLCLIENT_STATIC_LINKING=OFF \
    -DWITH_BOOST=${STAGING_INCDIR}/boost \
"

FILES:${PN}-dev = " \
    ${includedir}/mysql-cppconn/* \
    ${includedir}/mysql-cppconn-8/* \
    ${libdir}/libmysqlcppconn*.so \
    ${libdir}/libmysqlcppconn-*.so \
    ${libdir}/cmake/* \
"

FILES:${PN} = "${libdir}/libmysqlcppconn*.so.*"

BBCLASSEXTEND = "native nativesdk"
