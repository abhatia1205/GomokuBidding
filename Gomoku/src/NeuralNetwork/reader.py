import os
import numpy as np
from keras.models import load_model

b = True
path = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata"
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
		bet = arr.pop()
		arr_2 = line_array
		for i in range(len(arr)):
			for j in range(len(arr[i])):
				if arr[i][j] == 2:
					arr_2[i][j] = 2
					arr[i][j] = 0
		available = arr
		for i in range(len(arr)):
			for j in range(len(arr[i])):
				if arr[i][j] != 0 or arr_2[i][j]:
					available[i][j] = float("-inf")
		state = np.asarray([[[arr[i][j], arr_2[i][j] ] for j in range(len(arr))] for i in range(len(arr))]).reshape(1,1,450)
		bet = np.asarray(bet).reshape(1,1,2)
		model = load_model("/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/NeuralNetwork/gomoku_predictor.h5")
		better = load_model("/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/NeuralNetwork/gomoku_better.h5")
		index = np.argmax(model.predict(state).reshape(1,225) + np.asarray(available).reshape(1,225))
		action = (index//available.shape[0], index%available.shape[0])
		qvals1 = better.predict([state, bet]).reshape(100)
		bet_ret = np.argmax(np.array(qvals1)[:bet[0]+1])
		os.remove(st)
		try:
			os.remove(path+"/didthecringe.txt")
		except:
			print("Deleted did the dringe")
		f = open(path+"/didthecringe.txt", "x")
		f.write(str(bet_ret))
		f.write(str(action[0]) + " " + str(action[1]))
		f.close()





