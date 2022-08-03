#include <stdio.h>
#include <stdlib.h>

#define EMPTY_NODE 0
#define FILLED_NODE 1
#define DELETED_NODE -1

typedef struct hashtable_s
{
    int size;
    int *array;
    char *flag;
} HashTable;

void    HashInit(HashTable *hTable, int size)
{
    hTable->size = size;
    hTable->array = (int *)malloc(sizeof(int) * hTable->size);
    hTable->flag = (char *)malloc(sizeof(char) * hTable->size);
    for (int i = 0; i < hTable->size; i++)
        hTable->flag[i] = EMPTY_NODE;
}

unsigned int Hash(int key, int size)
{
    unsigned int hashValue = key;
    return (hashValue % size);
}

int CollsionFunction(int i)
{
    return (i);
}

int HashAdd(HashTable *hTable, int value)
{
    int hashValue = Hash(value, hTable->size);
    int i = 0;

    for (int i = 0; i < hTable->size; i++)
    {
        if (hTable->flag[hashValue] == EMPTY_NODE || hTable->flag[hashValue] == DELETED_NODE)
        {
            hTable->array[hashValue] = value;
            hTable->flag[hashValue] = FILLED_NODE;
            break;
        }
        hashValue += CollsionFunction(i);
        hashValue %= hTable->size;
    }

    if (i != hTable->size)
        return (1);
    else
        return (0);
}

int HashFind(HashTable *hTable, int value)
{
    int hashValue = Hash(value, hTable->size);
    for (int i = 0; i < hTable->size; i++)
    {
        if ((hTable->flag[hashValue] == FILLED_NODE && hTable->array[hashValue] == value) || hTable->flag[hashValue] == EMPTY_NODE)
            break;
        hashValue += CollsionFunction(i);
        hashValue %= hTable->size;
    }
    if (hTable->flag[hashValue] == FILLED_NODE && hTable->array[hashValue] == value)
        return (1);
    else
        return (0);
}

void    MinMaxMissing(int arr[], int size)
{
    HashTable hs;
    HashInit(&hs, size);

    int minVal = 999999;
    int maxVal = -999999;
    for (int i = 0; i < size; i++)
    {
        HashAdd(&hs, arr[i]);
        if (minVal > arr[i])
            minVal = arr[i];
        if (maxVal < arr[i])
            maxVal = arr[i];
    }

    printf("minVal = %d\n", minVal);
    printf("maxVal = %d\n", maxVal);
    for (int i = minVal; i <maxVal; i++)
    {
        if (HashFind(&hs, i) == 0)
            printf("MissingVal = %d\n", i);
    }
}

int main(void)
{
    int arr[10] = {1, 2, 3, 4, 5, 7, 8, 9, 10, 11};
    MinMaxMissing(arr, 10);
    return (0);
}