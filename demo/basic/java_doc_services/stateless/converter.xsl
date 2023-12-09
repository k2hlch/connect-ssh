<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="xml" encoding="ascii" indent="yes"/>
<xsl:template match="organisation">
<employee>
  <xsl:for-each select="employee">   
     <name> <xsl:apply-templates select="name"/> </name>
     <phone> <xsl:apply-templates select="contact/phone"/></phone>
  </xsl:for-each> 
</employee>
</xsl:template>
</xsl:stylesheet>
