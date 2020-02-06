#include <stdio.h>
#include <string.h>
#include <malloc.h>

//significant data to be stored by the Binary Search Tree
struct Book
{
	int isbn;
	float price;
};

// Binary Search Tree node structure
struct Node
{
	Node* leftSubT;
	Book* bookRef;
	Node* rightSubT;
};

// create/allocate new node inBinary Search Tree 
Node* createNode(Book* pc, Node* sLeft, Node* sRight) {
	Node* newNode = (Node*)malloc(1 * sizeof(Node));
	newNode->bookRef = pc;
	newNode->leftSubT = sLeft;
	newNode->rightSubT = sRight;
	return newNode;
}

//find position to insert the new node insert it
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

// parse the Binary Search Tree in ascending order of the keys (Book.isbn is the key for each node)
void dispTree_LeftRootRight(Node* r) {
	if (r) { // watch IP/EIP/RIP, BP/EBP/RBP and SP/ESP/RSP for each call in this code point
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