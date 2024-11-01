import './App.css'
import Display from './Display'
import { DisplayProvider } from './contexts/DisplayContext'

function App() {

  return (
    <DisplayProvider>
        <Display />
    </DisplayProvider>
  )
}

export default App
