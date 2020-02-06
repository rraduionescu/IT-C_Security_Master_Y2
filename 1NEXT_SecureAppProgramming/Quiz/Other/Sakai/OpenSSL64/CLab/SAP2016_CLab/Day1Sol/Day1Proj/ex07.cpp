//#include <stdio.h>
//#include <malloc.h>
//#include <string.h>
//
//struct Book
//{
//	int isbn;
//	char* title;
//	int pubYear;
//};
//
//struct Node
//{
//	Book info;
//	Node* next;
//};
//
//Node* insNodeTop(Node* v, Book* pc) {
//	Node* newNode = (Node*)malloc(1 * sizeof(Node));
//	/*newNode->info.isbn = pc->isbn;
//	newNode->info.title = pc->title;
//	newNode->info.pubYear = pc->pubYear;*/
//	newNode->info = *pc;
//	newNode->next = v;
//	return newNode;
//}
//
//void dispList(Node* v) {
//	Node* t = v;
//	while(t) {
//		printf("\n isbn = %d, title = %s, publishing year = %d", t->info.isbn, t->info.title, t->info.pubYear);
//		t = t->next;
//	}
//}
//
//void insNodeTop(Node** top, Book* inf) {
//	Node* temp = *top;
//	
//	*top = (Node*)malloc(1*sizeof(Node));
//	/*(*top)->info.isbn = inf->isbn;
//	(*top)->info.pubYear = inf->pubYear;
//	(*top)->info.title = (char*)malloc(strlen(inf->title)+1);
//	strcpy((*top)->info.title, inf->title);*/
//	(*top)->info = *inf;
//	(*top)->next = temp;
//}
//
//void deleteNodeStart(Node** v) {
//	Node* temp = *v;
//	if (temp != NULL) {
//		*v = (*v)->next;
//		free(temp);
//	}
//}
//
//void sortNodes(Node* v) {
//	if (!v || !v->next) return;
//	Node* i, *j;
//	Book temp;
//	for (i = v; i->next; i = i->next)
//		for (j = i->next; j; j = j->next)
//			if(i->info.isbn > j->info.isbn) {
//				temp = j->info;
//				j->info = i->info;
//				i->info = temp;
//			}
//}
//
//void main()
//{
//	Node* top = NULL;
//	Book bookItem;
//	char txtBuffer[10];
//
//	printf("ISBN = "); scanf("%d", &bookItem.isbn);
//	while(bookItem.isbn != 0) {
//		printf("TITLE="); scanf("%s", &txtBuffer);
//		bookItem.title = (char*)malloc((strlen(txtBuffer)+1) * sizeof(char));
//		strcpy(bookItem.title, txtBuffer);
//
//		printf("PUBLISHING YEAR="); scanf("%d", &bookItem.pubYear);
//
//		top = insNodeTop(top, &bookItem);
//		
//		printf("ISBN = "); scanf("%d", &bookItem.isbn);
//	}
//
//	printf("\n");
//	dispList(top);
//	deleteNodeStart (&top);
//	printf("\n");
//	dispList(top);
//	sortNodes(top);
//	printf("\n");
//	dispList(top);
//
//	//deallocate the remaining list
//	while(top) {
//		deleteNodeStart (&top);
//	}
//	printf("\nList after removing");
//	dispList(top);
//	printf("\n");
//
//}