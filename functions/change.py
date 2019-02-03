fh = open("songs.txt", 'r',encoding='UTF8')
fl = open("new.txt",'w')
first = fh.readline()
fl.write(first)

x = 0

for line in fh:
	line = line[:-1]
	l = "".split(line)
	print(l)
	line += ","
	line += str(x)
	x += 1
	line += "\n"
	try:
		fl.write(line)
	except:
		print("error")
		print(x)
		x -= 1
fl.close()
fh.close()
