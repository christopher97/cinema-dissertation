import sys
from sklearn.metrics.pairwise import cosine_similarity
from sklearn.feature_extraction.text import CountVectorizer

user = sys.argv[1]
movie = sys.argv[2]

count = CountVectorizer()
arr = [user, movie]

count_matrix = count.fit_transform(arr)
result = cosine_similarity(count_matrix, count_matrix)

print(result[0][1], end='')
