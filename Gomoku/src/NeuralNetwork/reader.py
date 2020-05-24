import os

b = True
path = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata"
while b:
	t = os.listdir(path)
	if len(t) != 0:
		st = -1
		for i in t:
			if ("cringer.txt" in i):
				print(i)
				st = path+ "/" + i
				break
		f = open(st, "r")
		arr = []
		line_array = f.readlines()
		length = len(line_array)
		if("end" in line_array[0]):
			break
		for s in line_array:
			s = [int(i) for i in s.split()]
			s.append(arr)
		arr_2 = line_array
		for i in range(len(arr)):
			for j in range(len(i)):
				if arr[i][j] == 2:
					arr_2[i][j] = 2
					arr[i][j] = 0
		print(s)
		os.remove(st)
		f = open(path+"/didthecringe.txt", "x")
		f.write("Number of lines is: "+ length)
		f.close()







