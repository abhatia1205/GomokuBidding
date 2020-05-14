class NeuralActor():

	def __init__(i, money = 50):
		self.money = money
		self.bet_model = makemodel()
		self.id = i
		self.opponent = 1

	def setOpponent(self, opponent):
		self.opponent = opponent

	def act(state, available):
		bet  = self.money + 1
		while bet > self.money:
			bet = model.predict(state, available)
			if(bet > self.money):
	
	def bet(self, state, bet):
		self.model.predict(state, bet)

	def makemodel(self):
		output_dim = self.money*2

