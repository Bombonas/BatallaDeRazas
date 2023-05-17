import pymysql.cursors
from lxml import etree
import os

# Llegir un arxiu XML
def read_xml(path):
   file = open(path, 'r', encoding='utf-8')
   string = file.read()
   file.close()
   return bytes(bytearray(string, encoding='utf-8'))

# Escriure un arxiu HTML
def write_html(path, html):
   file = open(path, 'w', encoding='utf-8')
   file.write(html)
   file.close()

# Crear un índex.html amb totes les cancons
def transform_index_cancons(xmlTree):
   # Crear l'arbre XSL per l'index de totes les cancons
   xslrounds = read_xml('xml/template-rounds.xsl')
   xslTreerounds = etree.XML(xslrounds)

   # Transformar l'arxiu de dades-cancons.xml segons l'arxiu template-cancons.xsl i guardar-lo a index.html
   transform = etree.XSLT(xslTreerounds)
   htmlDom = transform(xmlTree)
   htmlResult = etree.tostring(htmlDom, pretty_print=True).decode('utf-8')
   write_html("./html/index.html", htmlResult)

conn = pymysql.connect(
        host="localhost", port=3306,
        user="root", passwd="1234",
        charset='utf8mb4',
        db="raceWar"
    )

# Create a cursor
cursor = conn.cursor()

# Execute MySQL query to retrieve table schema
query = "SELECT * FROM rounds"
cursor.execute(query)

# Fetch the CREATE TABLE statement
result = cursor.fetchall()

text = "<?xml version=\"1.0\"?>\n<rounds>\n"

for row in result:
    text += "\t<round>\n" + \
            "\t\t<id>" + str(row[0]) + "</id>\n" + \
            "\t\t<battle_id>" + str(row[1]) + "</battle_id>\n" + \
            "\t\t<opponent_id>" + str(row[2]) + "</opponent_id>\n" + \
            "\t\t<opponent_weapon_id>" + str(row[3]) + "</opponent_weapon_id>\n" + \
            "\t\t<injuries_caused>" + str(row[4]) + "</injuries_caused>\n" + \
            "\t\t<injuries_suffered>" + str(row[5]) + "</injuries_suffered>\n" + \
            "\t\t<battle_points>" + str(row[6]) + "</battle_points>\n" + \
            "\t</round>\n"

text += "</rounds>"

xml = open("xml/rounds.xml", "w")
xml.write(text)
xml.close()

# Crear l'arbre XML
xml = read_xml("xml/rounds.xml")
xmlTree = etree.XML(xml)

# Esborrar els arxius .html creats anteriorment
for file in os.listdir("./html"):
   if file.endswith(".html"):
      os.remove("html/"+file)

# Generar l'índex de totes les cancons
transform_index_cancons(xmlTree)
