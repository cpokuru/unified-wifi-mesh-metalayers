SUMMARY = "MySQL database connector for C++"
DESCRIPTION = "MySQL Connector/C++ provides a C++ API for connecting client applications \
to the MySQL Server. It is designed for ease of use and familiar coding style \
for C++ developers."

HOMEPAGE = "https://dev.mysql.com/downloads/connector/cpp/"
LICENSE = "GPL-2.0-with-OpenSSL-exception"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d5ad936b92355df92ed0d61bc9b54577"

PV = "9.1.0"

DEPENDS = "boost openssl mariadb"

inherit cmake

SRC_URI = "https://cdn.mysql.com/Downloads/Connector-C++/mysql-connector-c++-${PV}-src.tar.gz"
SRC_URI[sha256sum] = "70fb6ca28ac154a5784090b3d8cc4f91636c208cf07c0000e3d22f72b557be13"
S = "${WORKDIR}/mysql-connector-c++-${PV}-src"

EXTRA_OECMAKE += " \
    -DCMAKE_INSTALL_PREFIX=${prefix} \
    -DINSTALL_LIB_DIR=${baselib} \
    -DINSTALL_INCLUDE_DIR=${includedir} \
    -DWITH_SSL=system \
    -DMYSQL_INCLUDE_DIR=${STAGING_INCDIR} \
    -DWITH_BOOST=${STAGING_INCDIR}/boost \
    -DBUILD_STATIC=OFF \
    -DBUILD_SHARED=ON \
    -DWITH_JDBC=ON \
    -DWITH_XDEVAPI=ON \
"

# Fix for parallel build issues
PARALLEL_MAKE = ""

FILES:${PN} = " \
    ${libdir}/lib*.so.* \
"

FILES:${PN}-dev = " \
    ${includedir}/mysql/* \
    ${includedir}/mysqlx/* \
    ${libdir}/lib*.so \
    ${libdir}/cmake/* \
"

# Add JDBC specific files
FILES:${PN}-jdbc = " \
    ${libdir}/libmysqlcppconn*.so.* \
"

FILES:${PN}-jdbc-dev = " \
    ${includedir}/mysql/jdbc.h \
    ${libdir}/libmysqlcppconn*.so \
"

FILES:${PN}-dbg += "${libdir}/.debug/lib*"

PACKAGES = "${PN} ${PN}-dev ${PN}-jdbc ${PN}-jdbc-dev ${PN}-dbg"

RDEPENDS:${PN} += "openssl mariadb"
RDEPENDS:${PN}-jdbc += "${PN}"

BBCLASSEXTEND = "native nativesdk"

COMPATIBLE_HOST = '(x86_64|i.86|arm|aarch64).*-linux'
