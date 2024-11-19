SUMMARY = "Unified Easy Mesh setup for Raspberry Pi"
DESCRIPTION = "Builds and configures Unified Easy Mesh for Raspberry Pi"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e0b1ae637439c7d6f4487fb90163c79a"

SRC_URI = "git://github.com/rdkcentral/unified-wifi-mesh.git;protocol=https;branch=main;name=unifiedmesh"

PV = "git${SRCPV}"

SRCREV_unifiedmesh = "${AUTOREV}"

DEPENDS = " \
    libev \
    jansson \
    zlib \
    libnl \
    avro-c \
    cjson \
    openssl \
    util-linux \
    readline \
    iptables \
    mariadb \
    dnsmasq \
    bridge-utils \
"


S = "${WORKDIR}/git"

do_configure() {
    # No configuration step needed since we are using custom Makefiles
    :
}


do_compile() {
    # Build Unified Easy Mesh
    cd ${S}/build/ctrl
    make all

    cd ../cli
    make all

    cd ../agent
    make all
}


do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/build/ctrl/onewifi_em_ctrl ${D}/usr/bin/
    install -m 0755 ${S}/build/cli/onewifi_em_cli ${D}/usr/bin/
    install -m 0755 ${S}/build/agent/onewifi_em_agent ${D}/usr/bin/
}


FILES_${PN} = "/usr/bin/*"

