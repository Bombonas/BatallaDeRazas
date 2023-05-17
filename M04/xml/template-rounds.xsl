<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/rounds">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>BattleRounds</title>
                <link rel="stylesheet" href="rounds.css"/>
            </head>
            <body>
				<table>
					<tr>
						<th>ID</th>
						<th>Battle ID</th>
						<th>Opponent ID</th>
						<th>Opponent Weapon ID</th>
						<th>Injuries Caused</th>
						<th>Injuries Suffered</th>
						<th>Defeat Points</th>
					</tr>
					<xsl:for-each select="round">
						<tr>
							<td><xsl:value-of select="id"/></td>
							<td><xsl:value-of select="battle_id"/></td>
							<td><xsl:value-of select="opponent_id"/></td>
							<td><xsl:value-of select="opponent_weapon_id"/></td>
							<td><xsl:value-of select="injuries_caused"/></td>
							<td><xsl:value-of select="injuries_suffered"/></td>
							<td><xsl:value-of select="battle_points"/></td>
						</tr>
					</xsl:for-each>
				</table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
