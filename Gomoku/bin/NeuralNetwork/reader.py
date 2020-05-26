import os
import numpy as np
from keras.models import load_model
import time

""" Code to interface with the GUI. Writes to a text file and reads from a text file, then imports the models to make a prediction"""
b = True
path = "/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/transferdata"
while b:
	t = os.listdir(path)
	if len(t) != 0:
		st = -1
		if ("cringer.txt" in t):
			print("YAY")
			st = path+ "/" + "cringer.txt"
			time.sleep(1)
		else:
			continue
		print(st)
		f = open(st, "r")
		arr = []
		line_array = f.readlines()
		print("Line Array: ", line_array)
		if("end" in line_array[0]):
			break
		for s in line_array:
			s = [int(i) for i in s.split()]
			arr.append(s)
		bet = arr.pop()
		arr_2 = arr
		for i in range(len(arr)):
			for j in range(len(arr[i])):
				if arr[i][j] == 2:
					arr_2[i][j] = 2
					arr[i][j] = 0
		available = arr
		for i in range(len(arr)):
			for j in range(len(arr[i])):
				if arr[i][j] != 0 or arr_2[i][j] != 0:
					available[i][j] = float("-inf")
		state = np.asarray([[[arr[i][j], arr_2[i][j] ] for j in range(len(arr))] for i in range(len(arr))]).reshape(1,1,450)
		print("Type is: ", type(state[0][0][0]))
		bets = np.asarray(bet).reshape(1,1,2)
		model = load_model("/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/NeuralNetwork/gomoku_predictor_2.h5")
		better = load_model("/home/anant/eclipse-workspace/GomokuBidding/Gomoku/src/NeuralNetwork/gomoku_better_2.h5")
		index = np.argmax(model.predict(state).reshape(1,225) + np.asarray(available).reshape(1,225))
		action = (index//15, index%15)
		print("ACTION IS: ", action)
		qvals1 = better.predict([state, bets]).reshape(100)
		print("QVALS is: ", qvals1)
		x = qvals1[:bet[0]+1]
		bet_ret = np.argmax(x)
		print("BET IS: ", bet_ret)
		os.remove(st)
		try:
			os.remove(path+"/didthecringe.txt")
		except:
			print("Deleted did the dringe")
		f = open(path+"/didthecringe.txt", "x")
		f.write(str(bet_ret) + " ")
		f.write(str(action[0]) + " " + str(action[1]))
		f.close()





