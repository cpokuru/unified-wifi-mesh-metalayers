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

inherit cmake

S = "${WORKDIR}/build"

do_compile() {
    # Clone OneWiFi and Unified Wi-Fi Mesh
    mkdir -p ${S}/easymesh_project
    cd ${S}/easymesh_project
    git clone --depth 1 https://github.com/rdkcentral/unified-wifi-mesh.git

    # Build Unified Easy Mesh
    cd ../unified-wifi-mesh/build/ctrl
    make all

    cd ../cli
    make all

    cd ../agent
    make all
}

do_install() {
    install -d ${D}/usr/bin
    cp ${S}/easymesh_project/OneWifi/build/linux/output/OneWifi ${D}/usr/bin/
    cp ${S}/easymesh_project/unified-wifi-mesh/build/ctrl/one_wifi_em_ctrl ${D}/usr/bin/
    cp ${S}/easymesh_project/unified-wifi-mesh/build/cli/one_wifi_em_cli ${D}/usr/bin/
    cp ${S}/easymesh_project/unified-wifi-mesh/build/agent/one_wifi_em_agent ${D}/usr/bin/
}

FILES_${PN} = "/usr/bin/*"

