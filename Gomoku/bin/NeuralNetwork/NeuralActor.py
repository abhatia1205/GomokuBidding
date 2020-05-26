import keras
from keras.models import Sequential
from keras.layers.normalization import BatchNormalization
from keras.layers.convolutional import MaxPooling2D
from keras.layers.core import Activation
from keras.layers.core import Dropout
from keras.layers.core import Dense
from keras.layers import Flatten
from keras.layers import Input
from keras.models import Model
from keras.layers import concatenate
from keras.layers.advanced_activations import LeakyReLU
import numpy as np

class NeuralActor():

	def __init__(self, i, money = 50):
		self.money = money
		self.model = self.makemodel()
		self.bet_model = self.makebetmodel()
		self.id = i
		self.opponent = 1
		self.prevQ = -1
		self.prevBetQ = -1
		self.prevAction = 0
		self.prevBet = -1
		self.bet_model.compile(optimizer = 'rmsprop', loss = 'mse')
		self.model.compile(optimizer = 'rmsprop', loss = 'mse')
		self.bet_model.summary()

	def setOpponent(self, opponent):
		self.opponent = opponent

	def getOpponent(self):
		return self.opponent

	def setPrevQ(self, q):
		self.prevQ = q

	def setPrevBetQ(self, q):
		self.prevBetQ = q

	def getPrevQ(self):
		return self.prevQ

	def getPrevBetQ(self):
		return self.prevBetQ

	def setPrevAction(self, action):
		self.prevAction = action

	def getPrevAction(self):
		return self.prevAction

	def setPrevBet(self, bet):
		self.prevBet = bet

	def getPrevBet(self):
		return self.prevBet

	def makemodel(self):
		model = Sequential()

		model.add(Dense(225, input_shape = (1, 2*15**2)))
		model.add(LeakyReLU(alpha=0.1))
		model.add(BatchNormalization())
		model.add(Dropout(0.2))
		model.add(Dense(225))
		model.add(LeakyReLU(alpha=0.1))
		model.add(BatchNormalization())
		model.add(Dropout(0.2))
		model.add(Dense(15**2, activation = 'sigmoid'))

		return model

	def makebetmodel(self):

		inputA = Input(shape=(1,2*15**2), name = 'state')
		inputB = Input(shape=(1,2), name = 'bets')
		# the first branch operates on the first input
		x = Dense(225, activation="relu")(inputA)
		x = LeakyReLU(alpha=0.1)(x)
		x = BatchNormalization()(x)
		x = Dense(10)(x)
		x = Model(inputs=inputA, outputs=x)
		# the second branch opreates on the second input
		y = Dense(10)(inputB)
		y = Model(inputs=inputB, outputs=y)
		# combine the output of the two branches
		combined = concatenate([x.output, y.output])
		# apply a FC layer and then a regression prediction on the
		# combined outputs
		z = Dense(225, activation="relu")(combined)
		z = LeakyReLU(alpha=0.1)(z)
		z = BatchNormalization()(z)
		z = Dense(150, activation="relu")(combined)
		z = LeakyReLU(alpha=0.1)(z)
		z = BatchNormalization()(z)
		z = Dense(100, activation = 'sigmoid') (z)
		# our model will accept the inputs of the two branches and
		# then output a single value
		model = Model(inputs=[x.input, y.input], outputs=z)
		return model

	def fit(self, x, y, batchsize=32):
		self.model.fit(x, y, batch_size=batchsize,
							  epochs=1)

	def bet_fit(self, x, x_bet,y, batchsize=32):
		print("Shape of labels are:", y.shape)
		self.bet_model.fit({'state': x, 'bets': x_bet}, y, batch_size=batchsize,
							  epochs=1)

	def predict(self, state): 
		x = self.model.predict(state.reshape(1,1,450))
		print ("Prediction is: ", x)
		x = x.reshape(1,225)
		return x

	def bet(self, state, bet):
		print("State is: ", state)
		print("Bet before prediction is: ", bet)
		x = self.bet_model.predict([state.reshape(1,1, 450), np.asarray(bet).reshape(1,1,2)])
		print("Bet qvals are: ", x)
		print("Bet shape is: ", x.shape)
		x = x.reshape(100)
		print("New x is: ", x)
		return x




