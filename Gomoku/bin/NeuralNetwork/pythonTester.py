import os

b = True
path = "Gomoku/src/transferdata"
while b:
	t = os.listdir(path)
	if len(t) != 0:
		st = -1
		for i in t:
			if ("cringer.txt" == i):
				print(i)
				st = path+ "/" + i
				break
		print(st)
		f = open(st, "r")
		arr = []
		line_array = f.readlines()
		if("end" in line_array[0]):
			break
		for s in line_array:
			s = [int(i) for i in s.split()]
			arr.append(s)
		arr_2 = line_array
		for i in range(len(arr)):
			for j in range(len(arr[i])):
				if arr[i][j] == 2:
					arr_2[i][j] = 2
					arr[i][j] = 0
		print(s)
		os.remove(st)
		try:
			os.remove(path+"/didthecringe.txt")
		except:
			print("Deleted did the dringe")
		f = open(path+"/didthecringe.txt", "x")
		f.write("Number of lines is: "+ str(arr))
		f.close()