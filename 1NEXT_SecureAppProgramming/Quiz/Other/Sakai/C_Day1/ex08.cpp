#include <stdio.h>
#include <string.h>
#include <malloc.h>

struct Book
{
	int isbn;
	float price;
};

struct Node
{
	Node* leftSubT;
	Book* bookRef;
	Node* rightSubT;
};

Node* createNode(Book* pc, Node* sLeft, Node* sRight) {
	Node* newNode = (Node*)malloc(1 * sizeof(Node));
	newNode->bookRef = pc;
	newNode->leftSubT = sLeft;
	newNode->rightSubT = sRight;
	return newNode;
}

Node* addNode(Node* root, Book* aBook) {
	Node* aux = NULL;
	if (!root) {
		return createNode(aBook, NULL, NULL);
	} else {
		aux = root;
		while(1) {
			if (aBook->isbn < root->bookRef->isbn) {
				if (root->leftSubT) {
					root = root->leftSubT;
				} else {
					root->leftSubT = createNode(aBook, NULL, NULL);
					return aux;
				}
			} else {
				if (aBook->isbn > root->bookRef->isbn) {
					if (root->rightSubT) {
						root = root->rightSubT;
					} else {
						root->rightSubT = createNode(aBook, NULL, NULL);
						return aux;
					}
				} else {
					//node is already in tree
					return aux;
				}
			}
		} //end while
	} //end else if(!root)
} //end addNode

void dispTree_LeftRootRight(Node* r) {
	if (r) {
		dispTree_LeftRootRight(r->leftSubT);
		printf("\n isbn = %d, price = %f", r->bookRef->isbn, r->bookRef->price);
		dispTree_LeftRootRight(r->rightSubT);
	}
}

void main()
{
	Node* treeRoot = NULL;
	Book* bookItem = NULL;

	bookItem = (Book*)malloc(1 * sizeof(Book));
	printf("isbn = "); scanf("%d", &bookItem->isbn);
	while (bookItem->isbn != 0) {
		printf("price = "); scanf("%f", &bookItem->price);

		treeRoot = addNode(treeRoot, bookItem);

		bookItem = (Book*)malloc(1 * sizeof(Book));
		printf("isbn = "); scanf("%d", &bookItem->isbn);
	}
	dispTree_LeftRootRight(treeRoot);
}