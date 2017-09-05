/**<p>Title: ReadOnlySet.java</p>
 * @Package com.hydra.blank.pojo
 * <p>Description: TODO</p>
 * @date 2017年7月25日 下午2:14:06
 * @version V1.0
 */
package com.hydra.blank.pojo;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**TODO
 * <p>
 * Created on 2017年7月25日 下午2:14:06
 * </p>
 * @since 2017年7月25日
 */
public class ReadOnlySet<E> implements Set<E> {

	/* (non-Javadoc)
	 * @Title: add
	 * @Description: TODO
	 * @param e
	 * @return
	 * @see java.util.Set#add(java.lang.Object)
	 */
	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: addAll
	 * @Description: TODO
	 * @param c
	 * @return
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: clear
	 * @Description: TODO
	 * @see java.util.Set#clear()
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: contains
	 * @Description: TODO
	 * @param o
	 * @return
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return set.contains(o);
	}

	/* (non-Javadoc)
	 * @Title: containsAll
	 * @Description: TODO
	 * @param c
	 * @return
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */
	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return set.containsAll(c);
	}

	/* (non-Javadoc)
	 * @Title: isEmpty
	 * @Description: TODO
	 * @return
	 * @see java.util.Set#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return set.isEmpty();
	}

	/* (non-Javadoc)
	 * @Title: iterator
	 * @Description: TODO
	 * @return
	 * @see java.util.Set#iterator()
	 */
	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return new ReadOnlySetIterator(set.iterator());
	}

	/* (non-Javadoc)
	 * @Title: remove
	 * @Description: TODO
	 * @param o
	 * @return
	 * @see java.util.Set#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: removeAll
	 * @Description: TODO
	 * @param c
	 * @return
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: retainAll
	 * @Description: TODO
	 * @param c
	 * @return
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @Title: size
	 * @Description: TODO
	 * @return
	 * @see java.util.Set#size()
	 */
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return set.size();
	}

	/* (non-Javadoc)
	 * @Title: toArray
	 * @Description: TODO
	 * @return
	 * @see java.util.Set#toArray()
	 */
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return set.toArray();
	}

	/* (non-Javadoc)
	 * @Title: toArray
	 * @Description: TODO
	 * @param a
	 * @return
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return set.toArray(a);
	}
	
	private class ReadOnlySetIterator implements Iterator<E> {

		/* (non-Javadoc)
		 * @Title: hasNext
		 * @Description: TODO
		 * @return
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return iterator.hasNext();
		}

		/* (non-Javadoc)
		 * @Title: next
		 * @Description: TODO
		 * @return
		 * @see java.util.Iterator#next()
		 */
		@Override
		public E next() {
			// TODO Auto-generated method stub
			return iterator.next();
		}
		
		private ReadOnlySetIterator(Iterator<E> iterator) {
			this.iterator = iterator;
		}
		
		private Iterator<E> iterator;
	}
	
	ReadOnlySet(Set<E> set){
		this.set = set;
	}
	
	private Set<E> set;
}
