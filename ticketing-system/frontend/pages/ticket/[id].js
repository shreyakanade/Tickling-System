import {useState,useEffect} from 'react';
import Router from 'next/router';
import {api} from '../../lib/api';
import {useRouter} from 'next/router';
export default function TicketDetail(){
  const router = useRouter();
  const {id} = router.query;
  const [ticket,setTicket]=useState(null);
  const [text,setText]=useState('');
  useEffect(()=>{ const t=localStorage.getItem('token'); if(!t) Router.push('/'); if(id) fetchTicket(t); },[id]);
  async function fetchTicket(t){ const r = await api('/tickets/'+id, localStorage.getItem('token'), {method:'GET'}); setTicket(r); }
  async function addComment(e){ e.preventDefault(); await api('/tickets/'+id+'/comment', localStorage.getItem('token'), {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({text})}); setText(''); fetchTicket(); }
  if(!ticket) return <div>Loading...</div>;
  return (<div><h2>Ticket #{ticket.id}: {ticket.subject}</h2><p>{ticket.description}</p><p>Status: {ticket.status} Priority: {ticket.priority}</p><h3>Comments</h3>{ticket.comments && ticket.comments.map(c=> (<div key={c.id}><b>{c.author ? c.author.username : 'user'}</b> ({c.createdAt}): <div>{c.text}</div></div>))}<form onSubmit={addComment}><textarea value={text} onChange={e=>setText(e.target.value)}></textarea><button>Add Comment</button></form><p><a onClick={()=>Router.push('/dashboard')}>Back</a></p></div>);
}
