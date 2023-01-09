package telran.list.model;

import java.util.Iterator;

import telran.list.interfaces.IList;

public class MyLinkedList<E> implements IList<E> {
	private Node<E> first;
	private Node<E> last;
	private int size;

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			Node<E> node = first;

			@Override
			public boolean hasNext() {
				return node != null;
			}

			@Override
			public E next() {
				E data = node.data;
				node = node.next;
				return data;
			}
		};
	}

	public void clear() {

//		first = null;
//		last = null;
//		size = 0;
		Node<E> node = first;
		while (node != null) {
			Node<E> nextNode = node.next;
			node.next = null;
			node.prev = null;
			node.data = null;
			node = nextNode;
		}
		size = 0;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(E element) {
		Node<E> newNode = new Node<>(last, element, null);
		if (last != null) {
			last.next = newNode;
		} else {
			first = newNode;
		}
		last = newNode;
		size++;
		return true;
	}

	@Override
	public boolean add(int index, E element) {
		if (index == size) {
			return add(element);
		}
		if (index == 0) {
			Node<E> newNode = new Node<>(null, element, first);
			first.prev = newNode;
			first = newNode;
		} else {
			Node<E> node = getNodeByIndex(index);
			Node<E> newNode = new Node<>(node.prev, element, node);
			node.prev = newNode;
			newNode.prev.next = newNode;
		}
		size++;
		return true;
	}

	private Node<E> getNodeByIndex(int index) {
		checkIndex(index);
		Node<E> node = first;
		for (int i = 0; i < index; i++) {
			node = node.next;
		}
		return node;
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index + " out of bounds");
		}

	}

	@Override
	public E get(int index) {
		Node<E> node = getNodeByIndex(index);
		return node.data;
	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o != null) {
			for (Node<E> node = first; node != null; node = node.next, index++) {
				if (o.equals(node.data)) {
					return index;
				}
			}
		} else {
			for (Node<E> node = first; node != null; node = node.next, index++) {
				if (o == node.data) {
					return index;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = size - 1;
		if (o != null) {
			for (Node<E> node = last; node != null; node = node.prev, index--) {
				if (o.equals(node.data)) {
					return index;
				}
			}
		} else {
			for (Node<E> node = last; node != null; node = node.prev, index--) {
				if (o == node.data) {
					return index;
				}
			}
		}
		return -1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E remove(int index) {
		checkIndex(index);
		Node<E> nodeForRemove = getNodeByIndex(index);
		if (nodeForRemove.equals(first)) {
			first = nodeForRemove.next;
			first.prev = null;
		} else if (nodeForRemove.equals(last)) {
			last = nodeForRemove.prev;
			last.next = null;

		} else {

			nodeForRemove.prev.next = nodeForRemove.next;
			nodeForRemove.next.prev = nodeForRemove.prev;
		}

		E victim = nodeForRemove.data;
		nodeForRemove = null;
		size--;
		return victim;

	}

	@Override
	public E set(int index, E element) {
		Node<E> node = getNodeByIndex(index);
		E victim = node.data;
		node.data = element;
		return victim;
	}

}
